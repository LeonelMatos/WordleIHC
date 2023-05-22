# Para Fazer

<img src="https://www.videogameschronicle.com/files/2022/01/wordle-d.jpg" width="250">

### Github

- [x] Criar repositório

## Interface

- [ ] Resisable
- [x] Multiple views solução

## Perfis

- [ ] Criar perfis com password
- [ ] Encriptar passwords dos perfis
- [ ] Editar perfis

## Leaderboard

- [ ] Pontuação
- [ ] Ranking (menu)

## GamePlay

- [ ] Trocar idioma (francês, inglês, português)
- [ ] Níveis de dificuldade (3->7)

## Encriptação

- [ ] Encontrar biblioteca de encriptação

# Notas

**Bibliotecas de palavras:**
- Collins Scrabble Words?
- CSW19
- [WordleCompetition Github (Inglês) -> 5 letters only](https://github.com/Kinkelin/WordleCompetition/tree/main/data/official)
- [Wiktionary](https://en.wiktionary.org/wiki/Wiktionary:Main_Page)

**Wordlists:**

- Ficheiro (txt ou json):
  - Wordlist com palavras (necessário separar pelos diferentes níveis e idiomas)
  - Necessário encriptar cada palavra da wordlist
- [Dicionário em Java](https://docs.oracle.com/javase/8/docs/api/java/util/Dictionary.html):
  - Palavras são carregadas com a aplicação
  - Não é necessário encriptar

# Recursos

- [Animações JavaFX](https://github.com/iAmGio/animated)
- [Encriptação Cipher](https://www.geeksforgeeks.org/encrypt-and-decrypt-string-file-using-java/)

# Interfaces

## Menu Inicial

**start-view (StartController)**

````
Menu inicial:
                
               (FOTO *1)
     TÍTULO
    
    (JOGAR)(Dificuldade *2)

  (DEFINIÇÕES)
  
     (SAIR)

````
_*1: Imagem do perfil atualmente selecionado. Ao clicar na imagem
abre o menu de perfis._
_*2: Dropdown da dificuldade do jogo (de 3 a 7 letras, ou seja, 5 níveis
de dificuldade)._

**game-view (GameController)**

````
Jogo:
(SAIR)        (DEFINIÇÕES)
    |-|-|-|-|
    |-|-|-|-|
    |-|-|-|-|
    
     (CHECK)*1
````
_*1: O check é feito automáticamente após completar a palavra_

**settings-view (SettingsController)**

````

````

**profile-view (ProfileController)**

````

````
