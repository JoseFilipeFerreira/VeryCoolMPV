# Especificações dos Use Cases

## Upload

**Descrição:** O user faz upload de media.

**Cenários:** O Fernando Fósforos faz upload de uma lista de músicas

**Pré-condição:** O user tem conta e o sistema está ativo

**Pós-condição:** O user fez upload e o media center tem a media no sistema

**Fluxo normal:**
1. O user fornece email e password
1. O media center valida a conta
1. O user indica quais media quer fazer upload
1. O sistema pede confirmação se realmente quer fazer upload dos arquivos
1. O user responde que sim
1. O media center recebe os arquivos e coloca-os disponíveis no sistema
1. O user classifica a sua media
1. O user cria uma biblioteca conforme os seus gostos
1. O user utiliza a media que fez upload
1. O user para a media
1. O user faz logout

**Fluxo alternativo 1:** [user ainda não possui conta]

1.1. O user fornece email e password

1.2. O media center recusa o login e mostra o aviso a dizer conta errad aou inexistente 

**Fluxo alternativo 2:** [user certifica-se que a sua conta existe]

2.1. O media center valida a conta

2.2. O user faz logout

**Fluxo alternativo 3:** [user só faz upload de media]

8.1. O user não utiliza a media que fez upload

8.2. O user faz logout

**Fluxo alternativo 4:** [user não confirma o upload]

5.1. O user responde que não

5.2. Regressa ao 2

**Fluxo alternativo 5:** [user não classifica o que fez upload]

7.1. O user não classifica a sua media

7.2. Continua para o 8

**Fluxo alternativo 6:** [user não cria bibliotecas]

8.1 O user não cria bibliotecas

8.2. Continua para o 9

**Fluxo alternativo 7:** [user usa media dos outros users]

9.1. O user utiliza a media que os outros users fizeram upload 

9.2. Continua para o 10


