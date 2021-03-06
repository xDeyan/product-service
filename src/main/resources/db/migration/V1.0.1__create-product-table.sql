CREATE TABLE PRODUCT (
    ID         		BIGINT             	PRIMARY KEY AUTO_INCREMENT,
    NAME        	VARCHAR(30)        	NOT NULL UNIQUE,
    CATEGORY    	VARCHAR(20)			NOT NULL,
    DESCRIPTION 	VARCHAR(200)		NOT NULL,
    QUANTITY    	INTEGER            	NOT NULL,
    CREATED_ON  	DATE            	NOT NULL,
    MODIFIED_ON 	DATE,
    CONSTRAINT PRODUCT_QTY_GT_0 CHECK (QUANTITY >= 0)
);