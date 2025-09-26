# Trabalho de Projeto de Software

## Alunos: 
 - João Vitor Garcia Aguiar Mintz; RA: 10440421
 - Giovanni ; RA: 10435745


## Contexto: 

Durante esta disciplina, você e sua equipe irão desenvolver, de forma incremental, um projeto de software completo, desde a concepção até a implementação em Java, utilizando UML para modelagem e GitHub para controle de versão.

Em suma: o projeto será desenvolvido de forma incremental, avaliando sua evolução a cada entrega, até a implementação final em Java. O foco é unir boa modelagem, implementação correta e segurança desde o início.

## O que deve ser feito:

 - Modelar e implementar um sistema de software em equipes de até 02 alunos.
 - Aplicar conceitos de análise e projeto orientado a objetos, utilizando UML (classes, sequência,colaboração e estados).
 - Implementar em Java, com versionamento e organização via GitHub.
 - Garantir que a segurança esteja presente desde a modelagem até a implementação (security by design).

## Como deve ser feito: 
 1. Modelagem inicial: elaborar diagramas de classes e sequência, respeitando boas práticas de encapsulamento e validação de dados.
 2. Integração dos modelos: consolidar classes, interações, estados e colaboração, prevendo restrições de segurança.
 3. Implementação: transformar os modelos em código Java, aplicando programação segura (validação de entradas, tratamento de exceções e uso de prepared statements).
 4. Controle de versão: utilizar GitHub desde a primeira entrega, com commits organizados, e sem credenciais ou dados sensíveis no repositório.
 5. Documentação e apresentação: gerar documentação clara, checklist de segurança e vídeo explicativo demonstrando o sistema.

## Entregáveis:
    Sprints:
        - ETAPA #1: Proposta de Projeto (tema, equipes e repositório GitHub) - 10/09.
        - ETAPA #2: Diagrama de Classes inicial - 17/09.
        - ETAPA #3: Diagrama de Sequência inicial - 24/09.
        - ETAPA #4: Integração de modelos (Classes + Banco de Dados) - 01/10.


        - ETAPA #5: Diagrama de Projeto (com abstrações e interfaces) - 22/10
        - ETAPA #6: Integração Sequência + Colaboração - 29/10
        - ETAPA #7: Diagrama de Estados - 05/11
        - ETAPA #8: Projeto Final (código Java, documentação e vídeo demo) - 19/11.

## Tema: 

Sistema de Controle de Drones para áreas rurais

### Contexto: 

Uma cooperativa rural deseja monitorar plantações usando drones que realizam sobrevoos periódicos para coletar imagens e dados ambientais (temperatura, umidade e pragas).

### Funcionalidades mínimas a implementar:

    - Cadastro de áreas agrícolas (tamanho, localização e tipo de cultivo).
    - Cadastro de drones (ID, sensores disponíveis e status).
    - Agendamento de missões de voo (data, área e sensores a utilizar).
    - Registro de dados coletados (imagens e valores de sensores simulados).
    - Relatórios básicos sobre condições da plantação (últimas medições e voos realizados).

### Requisitos de segurança:
    - Controle de acesso diferenciado (administrador e operador de drone).
    - Validação para impedir missões sobrepostas no mesmo drone.
    - Tratamento seguro de dados de sensores (evitar entradas inválidas ou corrompidas).
    - Checklist de segurança antes do voo (bateria mínima e sensores funcionando).

