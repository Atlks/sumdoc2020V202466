DELIMITER $$

DROP FUNCTION IF EXISTS `dev_kok_sport`.`score_byteamidMatchid` $$
CREATE FUNCTION `dev_kok_sport`.`score_byteamidMatchid` (matchid int,teamid int,teamtype int) RETURNS INT
BEGIN

select score into @sc from football_score_t where team_id=temid and match_id=matchid and team_type=teamtype limit 1;
return @sc;

END $$

DELIMITER ;