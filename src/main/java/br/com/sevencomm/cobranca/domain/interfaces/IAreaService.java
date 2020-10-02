package br.com.sevencomm.cobranca.domain.interfaces;

import br.com.sevencomm.cobranca.domain.models.Area;

import java.util.List;

public interface IAreaService {
    void deleteArea(Integer areaId);

    Area getAreaById(Integer areaId);
    Area insertArea(Area area);
    Area updateArea(Area area, Integer id);

    List<Area> listAreas();
    List<Area> listAreasByName(String areaName);

}
