package com.ctgu.sell.vo;

import com.ctgu.sell.dto.OrderDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * ClassName: SellerProductVo
 * Description:
 * date: 2019/8/3 23:21
 *
 * @author crwen
 * @since JDK 1.8
 */
@Data
public class OrderVo {

	@JsonProperty("pageNum")
	private Integer pageNum;
	@JsonProperty("pageSize")
	private Integer pageSize;
	@JsonProperty("totalPage")
	private Integer totalPage;
	@JsonProperty("total")
	private Integer total;

	@JsonProperty("list")
	private List<OrderDTO>  orderDTOList;

}
