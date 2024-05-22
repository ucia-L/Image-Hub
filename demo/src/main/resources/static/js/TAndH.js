function handleThumbUpClick(pid) {
    // 发送POST请求到后端触发点赞事件
    fetch('/thumbUp', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `pid=${pid}`, // 将画作ID作为请求参数发送到后端
    })
        .then(response => {
            // 根据返回状态进行逻辑处理
            if (response.ok) {
                // 更改图标样式为填充的粉色
                const thumbUpIcon = document.getElementById('thumbUpIcon_' + pid);
                thumbUpIcon.style.color = '#ea6c80'; // 可以设置颜色为粉色或其他您想要的颜色
            } else {
                // 处理请求失败的情况
                console.error('点赞请求失败');
            }
        })
        .catch(error => {
            console.error('发生错误:', error);
        });
}
function handleHeartClick(pid) {
    // 发送POST请求到后端触发收藏事件
    fetch('/heart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `pid=${pid}`, // 将画作ID作为请求参数发送到后端
    })
        .then(response => {
            // 根据返回状态进行逻辑处理
            if (response.ok) {
                // 更改图标样式为填充的粉色
                const heartIcon = document.getElementById('heartIcon_' + pid);
                heartIcon.style.color = 'red'; // 可以设置颜色为粉色或其他您想要的颜色
            } else {
                // 处理请求失败的情况
                console.error('收藏请求失败');
            }
        })
        .catch(error => {
            console.error('发生错误:', error);
        });
}

function deleteUser(uid) {
    axios.post('/deleteUser?uid=' + uid, {
        uid: uid
    }).then(({data}) => {
        if(data.success) {
            var elementToRemove = document.getElementById(uid);
            if (elementToRemove) {
                elementToRemove.remove(); // 删除元素
                alert('删除成功'); // 显示删除成功的提示框
            }
        } else {
            alert('删除失败')
        }
    })
}

function uploadUserType() {
    // 获取所有的 td 元素
    const tdElements = document.querySelectorAll('td.check-type');

    // 用于存储已勾选的 tr 元素的 ID
    let selectedTrIds = '';

    // 遍历每个 td 元素
    tdElements.forEach(td => {
        // 获取当前 td 元素的复选框
        const checkbox = td.querySelector('input[type="checkbox"]');

        // 检查复选框是否被勾选
        if (checkbox && checkbox.checked) {
            // 获取父级 tr 元素的 ID 并添加到字符串中
            const tr = td.closest('tr');
            if (tr) {
                if (selectedTrIds !== '') {
                    selectedTrIds += ','; // 如果不是第一个 ID，添加逗号分隔符
                }
                selectedTrIds += tr.id; // 添加当前 tr 元素的 ID
            }
        }
    });
    // 发送 POST 请求
    fetch('/uploadUserType', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'uids=' + selectedTrIds, // 将已勾选的 tr 元素的 ID 字符串作为请求体参数发送
    })
        .then(response => {
            // 处理响应
            if (response.redirected) {
                window.location.href = response.url; // 如果服务器返回了重定向，则跳转到重定向的页面
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}