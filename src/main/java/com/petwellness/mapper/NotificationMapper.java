package com.petwellness.mapper;

import com.petwellness.dto.NotificationDTO;
import com.petwellness.model.entity.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    private final ModelMapper modelMapper;
    public NotificationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public NotificationDTO toDTO(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }
    public Notification toEntity(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }
}
