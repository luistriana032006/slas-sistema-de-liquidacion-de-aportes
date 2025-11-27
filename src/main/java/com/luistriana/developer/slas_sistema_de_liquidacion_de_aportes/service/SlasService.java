package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.service;

import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.request.LiquidacionRequest;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.response.LiquidacionResponse;

public interface SlasService {
LiquidacionResponse calculoSlas(LiquidacionRequest request );

}
