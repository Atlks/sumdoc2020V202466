DROP VIEW IF EXISTS `football_odds_t_ex`;
CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `football_odds_t_ex` AS



select `football_odds_t`.`id` AS `id`,`football_odds_t`.`company_id` AS `company_id`,
`football_odds_t`.`match_id` AS `match_id`,`football_odds_t`.`odds_type` AS `odds_type`,`football_odds_t`.`change_time` AS `change_time`,`football_odds_t`.`happen_time` AS `happen_time`,`football_odds_t`.`match_status` AS `match_status`,`football_odds_t`.`home_odds` AS `home_odds`,`football_odds_t`.`tie_odds` AS `tie_odds`,`football_odds_t`.`away_odds` AS `away_odds`,`football_odds_t`.`lock_flag` AS `lock_flag`,`football_odds_t`.`real_time_score` AS `real_time_score`,`football_odds_t`.`create_time` AS `create_time`,`football_odds_t`.`delete_flag` AS `delete_flag`,
'bet365' AS `company`,0.92 AS `home_odds_chupan`,0.5 AS `tie_odds_chupan`,0.88 AS `away_odds_chupan`,
odds_type

 from `football_odds_t`;