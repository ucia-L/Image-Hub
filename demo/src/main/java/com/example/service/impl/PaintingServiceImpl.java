package com.example.service.impl;

import com.example.entity.Painting;
import com.example.mapper.PaintingMapper;
import com.example.service.PaintingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaintingServiceImpl implements PaintingService {
    @Resource
    PaintingMapper mapper;
    @Override
    public List<Painting> getAllPaintings() {
        return mapper.getAllPaintings();
    }

    @Override
    public List<Painting> getAllPaintingByType(int uid, String type) {
        return mapper.getAllPaintingByType(uid, type);
    }

    @Override
    public Painting getPaintingByPid(int pid) {
        return mapper.getPaintingByPid(pid);
    }

    @Override
    public int addPaintingDownloadCount(int pid) {
        return mapper.addPaintingDownloadCount(pid);
    }

    @Override
    public int addPaintingThumbUpCount(int pid) {
        return mapper.addPaintingThumbUpCount(pid);
    }

    @Override
    public int addPaintingHeartCount(int pid) {
        return mapper.addPaintingHeartCount(pid);
    }

    @Override
    public List<Painting> getPaintingByUid(int uid) {
        return mapper.getPaintingByUid(uid);
    }

    @Override
    public List<Painting> getPaintingByLabel(String label) {
        return mapper.getPaintingByLabel(label);
    }

    @Override
    public List<Painting> getPaintingByTitle(String title) {
        return mapper.getPaintingByTitle(title);
    }

    @Override
    public List<Painting> getPaintingByType(String type) {
        return mapper.getPaintingByType(type);
    }

    @Override
    public int uploadPaintingOfUid(Painting painting) {
        return mapper.uploadPaintingOfUid(painting);
    }

    @Override
    public boolean deletePaintingOfPid(int pid) {
        return mapper.deletePaintingOfPid(pid);
    }

    @Override
    public boolean deleteDHTOfPid(int pid) {
        return mapper.deleteDHTOfPid(pid);
    }
}
