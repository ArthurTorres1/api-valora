-- 1. Adiciona a nova coluna permitindo valores nulos
ALTER TABLE T_VLR_MOVIMENTACAO
    ADD ID_CONTA_DESTINO NUMBER(19);

-- 2. Cria a restrição de chave estrangeira (Constraint)
ALTER TABLE T_VLR_MOVIMENTACAO
    ADD CONSTRAINT FK_MOV_CONTA_DESTINO
        FOREIGN KEY (ID_CONTA_DESTINO)
            REFERENCES T_VLR_CONTA(ID_CONTA);