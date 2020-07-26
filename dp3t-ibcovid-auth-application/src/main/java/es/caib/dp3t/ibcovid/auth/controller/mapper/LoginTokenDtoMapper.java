package es.caib.dp3t.ibcovid.auth.controller.mapper;

import es.caib.dp3t.ibcovid.auth.controller.model.LoginRQDto;
import es.caib.dp3t.ibcovid.auth.controller.model.LoginTokenDto;
import es.caib.dp3t.ibcovid.auth.service.model.LoginRQSrvDto;
import es.caib.dp3t.ibcovid.auth.service.model.LoginTokenSrvDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginTokenDtoMapper {

    LoginTokenDtoMapper INSTANCE = Mappers.getMapper(LoginTokenDtoMapper.class);

    LoginRQSrvDto requestToSrvRequest(final LoginRQDto dto);

    LoginTokenDto srvDtoToDto(final LoginTokenSrvDto srvDto);

}
