/* DROP TABLE IF EXISTS customer;


 */
CREATE TABLE invoice(
   id VARCHAR(50) NOT NULL,
   partner VARCHAR(20) NOT NULL,
   companyId VARCHAR(20) NOT NULL,
   total DOUBLE NOT NULL,
   invoiceNumber VARCHAR(20) NOT NULL,
   date VARCHAR(20) NOT NULL,
   PRIMARY KEY (id)
);

DELETE FROM INVOICE;
