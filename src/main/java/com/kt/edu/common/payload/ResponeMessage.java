package com.kt.edu.common.payload;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponeMessage {

  private String responseType;
  
  private String responseCode;
  
  private String responseLogcd;
  
  private String responseTitle;
  
  private String responseBasc;
  
  private String responseDtal;
  
}
