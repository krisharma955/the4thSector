package com.K955.the4thSector.Mapper;

import com.K955.the4thSector.DTOs.User.UserProfileResponse;
import com.K955.the4thSector.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfileResponse toUserProfileResponse(User user);

}
