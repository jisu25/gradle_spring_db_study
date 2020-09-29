/* 신상품 4개 */

CREATE OR REPLACE VIEW NEW_PRO_VIEW
AS
SELECT PRODUCT_NO, PRODUCT_NAME, SALE, PRODUCT_IMAGE
FROM (
	SELECT ROWNUM, PRODUCT_NO, PRODUCT_NAME, SALE, PRODUCT_IMAGE
	FROM PRODUCT
	WHERE DEL_YN = 'n'
	ORDER BY REG_DATE DESC
)
WHERE ROWNUM <= 4;

SELECT PRODUCT_NO, PRODUCT_NAME, SALE, PRODUCT_IMAGE FROM new_pro_view;

SELECT PRODUCT_NO, PRODUCT_NAME, PRODUCT_KIND, PRICE, SALE, MARGIN, PRODUCT_CONTENT, PRODUCT_IMAGE, DEL_YN, BEST_YN, REG_DATE FROM product;

SELECT * FROM product;


/* 베스트 상품 */

create or replace view best_pro_view
as
select PRODUCT_NO, PRODUCT_NAME, SALE, PRODUCT_IMAGE
from( select ROWNUM, PRODUCT_NO, PRODUCT_NAME, SALE, PRODUCT_IMAGE
      from product  
      where best_yn='y' 
      order by reg_date desc)
where  rownum <=4;


-- 장바구니(cart)
create or replace view cart_view
as
SELECT CART_NO, c.MEMBER_ID , c.PRODUCT_NO , MEMBER_NAME, PRODUCT_NAME, QUANTITY, c.REG_DATE, SALE, RESULT 
  FROM cart c JOIN MEMBER m ON c.MEMBER_ID = m.MEMBER_ID JOIN PRODUCT p ON c.PRODUCT_NO = p.PRODUCT_NO
 WHERE result = '1';



--order view
create or replace view order_view
as
select ORDER_DETAIL_NO, o.ORDERS_NO, o.MEMBER_ID, ORDERS_DATE, d.PRODUCT_NO, ORDER_QUANTITY, MEMBER_NAME,
       ZIP_NUM, ADDRESS, PHONE, PRODUCT_NAME, SALE, RES_YN result
  from orders o JOIN order_detail d ON o.ORDERS_NO = d.ORDERS_NO JOIN member m ON o.MEMBER_ID =m.MEMBER_ID 
       JOIN product p ON d.PRODUCT_NO = p.PRODUCT_NO;