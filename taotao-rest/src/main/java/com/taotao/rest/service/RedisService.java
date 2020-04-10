package com.taotao.rest.service;

import com.taotao.common.uitl.TaotaoResult;

public interface RedisService {
	TaotaoResult syncContent(long contentCid);
}
