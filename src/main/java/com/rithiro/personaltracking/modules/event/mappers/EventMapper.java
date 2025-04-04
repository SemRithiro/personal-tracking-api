package com.rithiro.personaltracking.modules.event.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rithiro.personaltracking.modules.event.models.databases.SubscribedEvent;

@Mapper
public interface EventMapper {
    public List<SubscribedEvent> getSubscribedEvents(@Param("id") Integer id);
}
