/*
MySQL Backup
Source Server Version: 5.7.29
Source Database: dev_kok_sport
Date: 2020/5/23 10:46:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `subquerys`
-- ----------------------------
DROP TABLE IF EXISTS `subquerys`;
CREATE TABLE `subquerys` (
  `idint` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sql` text NOT NULL,
  `id` varchar(45) NOT NULL,
  PRIMARY KEY (`idint`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `subquerys` VALUES ('1',' ( select * from football_score_t where match_id={id} limit 1 ) as zhudui_scoreObj_frmdb','zhuduiSq'), ('2',' ( select * from football_score_t where team_type=2 and match_id={id} limit 1 ) as kedui_scoreObj_frmdb','keduiSq'), ('3',' ( select * from football_score_t where team_type=2 and match_id={match_id} limit 1 ) as kedui_scoreObj_frmdb','keduiSq_matchid');
