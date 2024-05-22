package com.example.service;

import com.example.entity.Painting;

import java.util.List;

public interface PaintingService {
    List<Painting> getAllPaintings();
    List<Painting> getAllPaintingByType(int uid, String type);
    Painting getPaintingByPid(int pid);
    int addPaintingDownloadCount(int pid);
    int addPaintingThumbUpCount(int pid);
    int addPaintingHeartCount(int pid);
    List<Painting> getPaintingByUid(int uid);
    List<Painting> getPaintingByLabel(String label);
    List<Painting> getPaintingByTitle(String title);
    List<Painting> getPaintingByType(String type);
    int uploadPaintingOfUid(Painting painting);
    boolean deletePaintingOfPid(int pid);
    boolean deleteDHTOfPid(int pid);
}
