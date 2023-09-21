package com.feeham.social.postfeed.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostReadDTO {
    private LocalDateTime createTime;
    private String content;
    private String userFirstName;
    private String userLastName;
}
