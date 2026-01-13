# APIJava

Montar uma API Java Spring.

- Versão do Java 21

O que a API deve fazer?

1 - Ter um endpoint capaz de armazenar mensagens em um banco de dados, H2 ou MySQL.
2 - Ao armazenar essa mensagem, ela precisa ter uma CHAVE, data de criação e a MENSAGEM em SI.


Exemplo do objeto salvo:

ID
CHAVE_MENSAGEM
MENSAGEM
DATA_CRIACAO


Então no minimo na hora de salvar pelo endpoint, precisa passar a CHAVE e a MENSAGEM.

PORQUE a CHAVE?

Depois do endpoint de armazenamento criado, a segunda etapa é a criação de um endpoint que ao passar a CHAVE me retorne a MENSAGEM.
 
