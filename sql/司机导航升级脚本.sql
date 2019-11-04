CREATE TABLE `epp_address`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `epp_code`     varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工程代号',
    `address`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '地址',
    `create_user`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime                               DEFAULT NULL COMMENT '创建时间',
    `order`        int(11)                                DEFAULT NULL COMMENT '排序',
    `status`       bit(1)                                 DEFAULT NULL COMMENT '状态',
    `address_type` int(11)                                DEFAULT NULL COMMENT '地址类型',
    `compid`       varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `driver_location`
(
    `dl_id`          int(11) NOT NULL AUTO_INCREMENT,
    `dl_epp_code`    varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工程代号',
    `dl_tsi_id`      int(20)                                DEFAULT NULL COMMENT '小票id',
    `dl_driver_code` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '司机id',
    `dl_car_id`      varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '车辆id',
    `address`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '地址',
    `create_time`    datetime                               DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`dl_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;