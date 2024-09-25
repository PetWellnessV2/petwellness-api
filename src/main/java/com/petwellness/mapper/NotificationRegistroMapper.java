package com.petwellness.mapper;

import com.petwellness.dto.NotificationRegistroDTO;
import com.petwellness.model.entity.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NotificationRegistroMapper {
    private final ModelMapper modelMapper;
    public NotificationRegistroMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public NotificationRegistroDTO toDTO(Notification notification) {
        NotificationRegistroDTO notificationRegistroDTO = modelMapper.map(notification, NotificationRegistroDTO.class);
        notificationRegistroDTO.setUsuarioId(notification.getUsuario().getUserId());
        return notificationRegistroDTO;
    }

    public Notification toEntity(NotificationRegistroDTO notificationRegistroDTO) {
        return modelMapper.map(notificationRegistroDTO, Notification.class);
    }
}
