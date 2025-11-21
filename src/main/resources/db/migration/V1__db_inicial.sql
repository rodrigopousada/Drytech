


CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    tipo ENUM('ADMIN', 'COMUM') NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE usuario_interesses (
    usuario_id INT NOT NULL,
    categoria_id INT NOT NULL,
    PRIMARY KEY (usuario_id, categoria_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE
);

CREATE TABLE recursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(100),
    categoria_id INT NOT NULL,
    usuario_id INT NOT NULL,
    url VARCHAR(255),
    descricao TEXT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);
INSERT INTO usuarios (nome, idade, tipo, username, password_hash, ativo) VALUES
('Administrador Padrão', 30, 'ADMIN', 'admin', 'admin123', TRUE),
('Felipe Muniz', 21, 'COMUM', 'felipe', '12345', TRUE),
('Neymar Junior', 22, 'COMUM', 'neymar', '12345', TRUE);

INSERT INTO categorias (nome) VALUES
('IA Responsável'),
('Cibersegurança'),
('Privacidade & Ética Digital');

INSERT INTO usuario_interesses (usuario_id, categoria_id)
VALUES
((SELECT id FROM usuarios WHERE username = 'felipe'),
 (SELECT id FROM categorias WHERE nome = 'IA Responsável')),
((SELECT id FROM usuarios WHERE username = 'felipe'),
 (SELECT id FROM categorias WHERE nome = 'Cibersegurança')),
((SELECT id FROM usuarios WHERE username = 'neymar'),
 (SELECT id FROM categorias WHERE nome = 'Privacidade & Ética Digital'));

INSERT INTO recursos (titulo, autor, categoria_id, usuario_id, url, descricao) VALUES
('Introdução à IA Responsável', 'Andrew Ng',
 (SELECT id FROM categorias WHERE nome = 'IA Responsável'),
 (SELECT id FROM usuarios WHERE username = 'felipe'),
 'https://deeplearning.ai/ai-responsavel',
 'Guia introdutório sobre princípios éticos e responsáveis no uso de IA.'),

('Fundamentos de Cibersegurança', 'Kaspersky Academy',
 (SELECT id FROM categorias WHERE nome = 'Cibersegurança'),
 (SELECT id FROM usuarios WHERE username = 'felipe'),
 'https://kaspersky.com/fundamentos-ciberseguranca',
 'Curso introdutório sobre segurança digital e ameaças cibernéticas.'),

('Privacidade e Ética Digital', 'Universidade de São Paulo',
 (SELECT id FROM categorias WHERE nome = 'Privacidade & Ética Digital'),
 (SELECT id FROM usuarios WHERE username = 'neymar'),
 'https://usp.br/curso-etica-digital',
 'Material sobre ética, privacidade e responsabilidade digital.');
