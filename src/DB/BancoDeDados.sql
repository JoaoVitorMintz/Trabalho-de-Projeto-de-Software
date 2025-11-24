-- Usuario
CREATE TABLE Usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    setor VARCHAR(255)
);

-- Autorizacao
CREATE TABLE Autorizacao (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_autorizacao_usuario FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

-- Operador
CREATE TABLE Operador (
    id INTEGER PRIMARY KEY,
    CONSTRAINT fk_operador_usuario FOREIGN KEY (id) REFERENCES Usuario(id)
);

-- Administrador
CREATE TABLE Administrador (
    id INTEGER PRIMARY KEY,
    CONSTRAINT fk_admin_usuario FOREIGN KEY (id) REFERENCES Usuario(id)
);

-- Drone
CREATE TABLE Drone (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50),
    modelo VARCHAR(100)
);

-- Checklist
CREATE TABLE Checklist (
    id SERIAL PRIMARY KEY,
    bateria NUMERIC(10,2),
    sensores_funcionais BOOLEAN,
    drone_id INTEGER,
    CONSTRAINT fk_checklist_drone FOREIGN KEY (drone_id) REFERENCES Drone(id)
);

-- Sensor
CREATE TABLE Sensor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    drone_id INTEGER,
    CONSTRAINT fk_sensor_drone FOREIGN KEY (drone_id) REFERENCES Drone(id)
);

-- Sensor Temperatura
CREATE TABLE SensorTemperatura (
    id INTEGER PRIMARY KEY,
    temperatura NUMERIC(10,2),
    CONSTRAINT fk_temp_sensor FOREIGN KEY (id) REFERENCES Sensor(id)
);

-- Sensor Umidade
CREATE TABLE SensorUmidade (
    id INTEGER PRIMARY KEY,
    umidade NUMERIC(10,2),
    CONSTRAINT fk_umidade_sensor FOREIGN KEY (id) REFERENCES Sensor(id)
);

-- Sensor Infravermelho
CREATE TABLE SensorInfravermelho (
    id INTEGER PRIMARY KEY,
    ativo BOOLEAN,
    CONSTRAINT fk_ir_sensor FOREIGN KEY (id) REFERENCES Sensor(id)
);

-- Cadastro Areas
CREATE TABLE CadastroAreas (
    id SERIAL PRIMARY KEY,
    tamanho NUMERIC(10,2),
    localizacao VARCHAR(255),
    tipo_cultivo VARCHAR(100)
);

-- Missoes de Voo
CREATE TABLE MissoesVoo (
    id SERIAL PRIMARY KEY,
    id_missao VARCHAR(50) UNIQUE NOT NULL,
    data DATE NOT NULL,
    drone_id INTEGER NOT NULL,
    area_id INTEGER NOT NULL,
    CONSTRAINT fk_missao_drone FOREIGN KEY (drone_id) REFERENCES Drone(id),
    CONSTRAINT fk_missao_area FOREIGN KEY (area_id) REFERENCES CadastroAreas(id),
    CONSTRAINT uq_missao_drone_data UNIQUE (drone_id, data)
);

-- Registro de Dados
CREATE TABLE RegistroDados (
    id SERIAL PRIMARY KEY,
    missao_id INTEGER,
    CONSTRAINT fk_registro_missao FOREIGN KEY (missao_id) REFERENCES MissoesVoo(id)
);

-- Relatorio
CREATE TABLE Relatorio (
    id SERIAL PRIMARY KEY,
    medicoes NUMERIC(10,2),
    qtd_voo INTEGER,
    registro_id INTEGER,
    CONSTRAINT fk_relatorio_registro FOREIGN KEY (registro_id) REFERENCES RegistroDados(id)
);
