database table doc

<!-- MarkdownTOC -->

- user_bus_day_report     COMMENT='会员per day报表';

<!-- /MarkdownTOC -->


## user_bus_day_report     COMMENT='会员per day报表';

`cp_bet_money` double(20,3) DEFAULT 0.000 COMMENT '投注总金额',
  `cp_win_money` double(20,3) DEFAULT 0.000 COMMENT '中奖总金额',
  `cp_draw_money` double(20,3) DEFAULT 0.000 COMMENT '合局金额',
  `cp_rebate_money` double(20,3) DEFAULT 0.000 COMMENT '返水金额',
  `cp_bet_count` int(11) DEFAULT 0 COMMENT '彩票下注笔数',