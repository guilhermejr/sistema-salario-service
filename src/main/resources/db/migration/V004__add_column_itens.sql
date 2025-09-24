ALTER TABLE itens ADD COLUMN unidade numeric(19,2) ;
UPDATE itens SET unidade = 0.00;