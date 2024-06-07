data struts sql

SELECT distinct	c.TABLE_NAME 数据结构名, 	table_comment 含义,  	COLUMN_NAME 字段名, 	COLUMN_COMMENT 备注含义 ,DATA_TYPE 数据类型 	FROM 	information_schema.`COLUMNS` c 

left join information_schema.`TABLES` t on c.TABLE_NAME=t.TABLE_NAME  and      t.table_schema='dev_kok_sport' 

WHERE    c.table_schema='dev_kok_sport' and 	c.TABLE_NAME = 'football_tlive_v'



 