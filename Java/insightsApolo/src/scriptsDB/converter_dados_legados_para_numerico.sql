-- ========================================
-- Script para converter dados legados para formato numérico
-- Execute este script no banco apoloInsights
-- ========================================

-- 1. CONVERTER ROLE DE STRING PARA INTEGER
-- ========================================

-- Verificar valores atuais
SELECT DISTINCT role FROM usuarios ORDER BY role;

-- Adicionar coluna temporária
ALTER TABLE usuarios ADD COLUMN role_temp TINYINT;

-- Converter valores string para numérico
UPDATE usuarios SET role_temp = 0 WHERE role = 'CLIENTE' OR role = '0';
UPDATE usuarios SET role_temp = 1 WHERE role = 'ADMIN' OR role = '1';
UPDATE usuarios SET role_temp = 2 WHERE role = 'ESTETICISTA' OR role = '2';
UPDATE usuarios SET role_temp = 3 WHERE role = 'CABELEIREIRO' OR role = '3';
UPDATE usuarios SET role_temp = 4 WHERE role = 'MANICURE_PEDICURE' OR role = '4';

-- Definir valor padrão para roles desconhecidos
UPDATE usuarios SET role_temp = 0 WHERE role_temp IS NULL;

-- Verificar conversão
SELECT role AS old_value, role_temp AS new_value, COUNT(*) AS count 
FROM usuarios 
GROUP BY role, role_temp;

-- Remover coluna antiga e renomear
ALTER TABLE usuarios DROP COLUMN role;
ALTER TABLE usuarios CHANGE COLUMN role_temp role TINYINT NOT NULL DEFAULT 0;

-- 2. CONVERTER DIA_SEMANA DE STRING PARA INTEGER (horarios_disponiveis)
-- ========================================

-- Verificar valores atuais
SELECT DISTINCT dia_semana FROM horarios_disponiveis ORDER BY dia_semana;

-- Adicionar coluna temporária
ALTER TABLE horarios_disponiveis ADD COLUMN dia_semana_temp TINYINT;

-- Converter valores string/numéricos para padrão 0-6
UPDATE horarios_disponiveis SET dia_semana_temp = 0 WHERE dia_semana = 'SUNDAY' OR dia_semana = '0' OR dia_semana = 0 OR dia_semana = 7;
UPDATE horarios_disponiveis SET dia_semana_temp = 1 WHERE dia_semana = 'MONDAY' OR dia_semana = '1' OR dia_semana = 1;
UPDATE horarios_disponiveis SET dia_semana_temp = 2 WHERE dia_semana = 'TUESDAY' OR dia_semana = '2' OR dia_semana = 2;
UPDATE horarios_disponiveis SET dia_semana_temp = 3 WHERE dia_semana = 'WEDNESDAY' OR dia_semana = '3' OR dia_semana = 3;
UPDATE horarios_disponiveis SET dia_semana_temp = 4 WHERE dia_semana = 'THURSDAY' OR dia_semana = '4' OR dia_semana = 4;
UPDATE horarios_disponiveis SET dia_semana_temp = 5 WHERE dia_semana = 'FRIDAY' OR dia_semana = '5' OR dia_semana = 5;
UPDATE horarios_disponiveis SET dia_semana_temp = 6 WHERE dia_semana = 'SATURDAY' OR dia_semana = '6' OR dia_semana = 6;

-- Verificar conversão
SELECT dia_semana AS old_value, dia_semana_temp AS new_value, COUNT(*) AS count 
FROM horarios_disponiveis 
GROUP BY dia_semana, dia_semana_temp;

-- Remover valores nulos se existirem
DELETE FROM horarios_disponiveis WHERE dia_semana_temp IS NULL;

-- Remover coluna antiga e renomear
ALTER TABLE horarios_disponiveis DROP COLUMN dia_semana;
ALTER TABLE horarios_disponiveis CHANGE COLUMN dia_semana_temp dia_semana TINYINT NOT NULL;

-- 3. LIMPAR DADOS NULOS EM BLOQUEIOS (se necessário)
-- ========================================

-- Verificar bloqueios com dia_semana nulo
SELECT COUNT(*) FROM bloqueio_semanal WHERE dia_semana IS NULL;

-- Remover bloqueios com dia_semana nulo (ou atualizar conforme necessário)
DELETE FROM bloqueio_semanal WHERE dia_semana IS NULL;

-- Verificar bloqueios com data nula
SELECT COUNT(*) FROM bloqueio_especifico WHERE data IS NULL;

-- Remover bloqueios com data nula (ou atualizar conforme necessário)
DELETE FROM bloqueio_especifico WHERE data IS NULL;

-- 4. ADICIONAR CAMPO BLOQUEADO SE NÃO EXISTIR
-- ========================================

-- Verificar se coluna existe
SELECT COUNT(*) 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = 'apoloInsights' 
  AND TABLE_NAME = 'horarios_disponiveis' 
  AND COLUMN_NAME = 'bloqueado';

-- Se retornar 0, adicionar a coluna:
ALTER TABLE horarios_disponiveis ADD COLUMN bloqueado BIT NOT NULL DEFAULT 0;

-- 5. VERIFICAÇÕES FINAIS
-- ========================================

-- Verificar estrutura da tabela usuarios
DESCRIBE usuarios;

-- Verificar estrutura da tabela horarios_disponiveis
DESCRIBE horarios_disponiveis;

-- Verificar dados convertidos
SELECT id, role FROM usuarios LIMIT 10;
SELECT id, dia_semana, bloqueado FROM horarios_disponiveis LIMIT 10;

-- ========================================
-- IMPORTANTE: Execute este script passo a passo
-- e verifique os resultados antes de continuar!
-- ========================================
