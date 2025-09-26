# Diagrama de classe:

```
'''@startuml
' =============================
' Classes de Sensores
' =============================
class Sensores {
  M ValidarSensores()
}

class SensorTemperatura {
  - DOUBLE Temperatura
  M ValidarTemperatura()
}

class SensorUmidade {
  - DOUBLE Umidade
  M ValidarUmidade()
}

class SensorInfravermelho {
  - BOOL Ativo
  M ValidarPragas()
}

Sensores <|-- SensorTemperatura
Sensores <|-- SensorUmidade
Sensores <|-- SensorInfravermelho

' =============================
' Classes de Drones e Missões
' =============================
class Drones {
  - INT ID
  - Sensores sensores
  - STRING Status
}

class "Missões de voo" as MissoesVoo {
  - INT Data
  - STRING IDMissao
  - DOUBLE Area
  M AtribuirDrone()
}

class "Cadastro Áreas" as CadastroAreas {
  - DOUBLE Tamanho
  - DOUBLE Localizacao
  - STRING TipoCultivo
}

class "Registro de Dados" as RegistroDados {
  - Imagens
  - LIST<DOUBLE> ValoresSensores
}

class Relatorio {
  - DOUBLE Medições
  - INT Qtd_Voo
}

MissoesVoo <-- Drones
Drones --> Sensores
Drones --> CadastroAreas
MissoesVoo --> RegistroDados
RegistroDados --> Relatorio : "Extensão"

' =============================
' Outras Classes
' =============================

class Autorizacao {
  M Role()
  ' Verificação de role ADM ou Operador
}

class Checklist {
  - DOUBLE Bateria
  - LIST<BOOL> SensoresFunc
}
@enduml'''
```
