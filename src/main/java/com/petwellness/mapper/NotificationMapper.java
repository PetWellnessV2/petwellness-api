package com.petwellness.mapper;

import com.petwellness.dto.NotificationDTO;
import com.petwellness.model.entity.Customer;
import com.petwellness.model.entity.Notification;
import com.petwellness.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationMapper {
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    public NotificationDTO toDTO(Notification notification) {
        NotificationDTO notificationDTO = modelMapper.map(notification, NotificationDTO.class);
        notificationDTO.setUsuarioId(notification.getUsuario().getUserId());
        return notificationDTO;
    }
    public Notification toEntity(NotificationDTO notificationDTO) {
        Notification notification = modelMapper.map(notificationDTO, Notification.class);
        if (notificationDTO.getUsuarioId() != null) {
            Customer usuario = customerRepository.findById(notificationDTO.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            notification.setUsuario(usuario);
        }
        return notification;
    }
}
