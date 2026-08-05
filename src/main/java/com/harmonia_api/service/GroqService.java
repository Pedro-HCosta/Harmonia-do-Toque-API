package com.harmonia_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harmonia_api.client.GroqClient;
import com.harmonia_api.dto.AnaliseIADTO;
import com.harmonia_api.dto.FichaDTO;
import com.harmonia_api.dto.RequisicaoGroqDTO;
import com.harmonia_api.dto.RespostaGroqDTO;
import org.springframework.stereotype.Service;

@Service
public class GroqService {

    private final GroqClient groqClient;
    private final ObjectMapper objectMapper;

    public GroqService(GroqClient groqClient, ObjectMapper objectMapper) {
        this.groqClient = groqClient;
        this.objectMapper = objectMapper;
    }

    public AnaliseIADTO analisarFicha(FichaDTO ficha) {

        String prompt = """
Você é um musicoterapeuta experiente especializado em psicologia da música.

Sua tarefa é analisar o estado emocional do usuário e produzir recomendações musicais que possam ajudá-lo.

IMPORTANTE:

- Responda APENAS um JSON válido.
- Não utilize Markdown.
- Não escreva explicações fora do JSON.
- Não utilize ```json.
- Não invente campos.

Considere:

- idade
- profissão
- sintomas
- objetivo da sessão
- descrição emocional
- gêneros favoritos
- gêneros que o usuário NÃO gosta
- preferências musicais

Regras:

1. Nunca recomende gêneros presentes na lista de gêneros que o usuário não gosta.

2. Escolha EXATAMENTE 3 gêneros musicais.

3. Escolha EXATAMENTE 5 artistas.

4. Os artistas devem:

- existir;
- ser populares;
- possuir músicas disponíveis no Spotify;
- combinar com o estado emocional do usuário.

5. Gere EXATAMENTE 5 palavras-chave para busca no Spotify.

As palavras devem ser curtas.

Exemplos:

study
focus
calm
ambient
instrumental

6. Defina uma faixa de BPM adequada.

7. O raciocínio deve possuir no máximo 4 frases.

8. O humor identificado deve possuir apenas uma palavra ou expressão curta.

Exemplos:

Ansioso
Estressado
Calmo
Melancólico
Motivado
Cansado
Esperançoso

Retorne exatamente este formato:

{
  "raciocinio":"...",
  "humorIdentificado":"...",
  "bpmMin":70,
  "bpmMax":90,
  "generos":[
    "...",
    "...",
    "..."
  ],
  "artistas":[
    "...",
    "...",
    "...",
    "...",
    "..."
  ],
  "palavrasChave":[
    "...",
    "...",
    "...",
    "...",
    "..."
  ]
}

========================
FICHA DO USUÁRIO
========================

Nome: %s

Idade: %d

Sexo: %s

Profissão: %s

Primeira sessão: %s

Sintomas:
%s

Descrição dos sentimentos:
%s

Objetivo da sessão:
%s

Gêneros favoritos:
%s

Gêneros que não gosta:
%s

Preferências musicais:
%s
"""
.formatted(
        ficha.nome(),
        ficha.idade(),
        ficha.sexo(),
        ficha.profissao(),
        ficha.primeiraSessao(),
        String.join(", ", ficha.sintomas()),
        ficha.descricaoSentimentos(),
        ficha.objetivoSessao(),
        String.join(", ", ficha.generosFavoritos()),
        String.join(", ", ficha.generosNaoGosta()),
        ficha.preferenciasMusicais()
);

        RequisicaoGroqDTO requisicao = new RequisicaoGroqDTO(
                "llama-3.3-70b-versatile",
                java.util.List.of(
                        new RequisicaoGroqDTO.Message(
                                "user",
                                prompt
                        )
                ),
                0.7
        );
        
        RespostaGroqDTO resposta = groqClient.gerarResposta(requisicao);

        try {

            String json = resposta.choices()
                    .getFirst()
                    .message()
                    .content()
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return objectMapper.readValue(json, AnaliseIADTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao interpretar resposta do Groq.", e);
        }
    }
}