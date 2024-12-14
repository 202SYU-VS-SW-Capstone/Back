package com.ohgiraffers.recipeapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 추가 요청 DTO") // Swagger 문서화용
public class MemberRequestDto {

    @Schema(description = "회원 닉네임", required = true, example = "Tom")
    @NotBlank(message = "닉네임은 비워둘 수 없습니다.")
    private String nickname;

    @Schema(description = "회원 이름", required = true, example = "Tom")
    @NotBlank(message = "이름은 비워둘 수 없습니다.")
    private String name;

    @Schema(description = "회원 이메일", required = true, example = "Tom123@aaa.com")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "비밀번호", required = true, example = "Qqwert123#")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상, 최대 20자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}$",
            message = "비밀번호는 대소문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다.")
    private String password;

    @Schema(description = "회원 계정 타입", required = true, example = "CONSUMER")
    @NotBlank(message = "계정 타입은 필수 입력 값입니다.")
    @Pattern(regexp = "^(CONSUMER|SELLER)$", message = "계정 타입은 'CONSUMER' 또는 'SELLER'이어야 합니다.")
    private String accountType;
}
