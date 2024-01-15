import kotlin.math.*

/*
Enunciado 8:
Num recinto de jogos radicais um canhão lança um jogador que tem de atingir uma rede vertical de
segurança que se encontra à distância horizontal de 40m do canhão. A rede tem uma altura de 5m.
(a) - Determine valores de velocidade inicial e de ângulo de lançamento que permitem que o jogador atinja
a rede de segurança;
(b) - Trace a trajétoria do jogador durante o voo.
O programa deve permitir alterar as condições iniciais do lançamento para poder simular diferentes
cenários de voo.
Sugestão: Calcule também o tempo de voo e tempo de subida.
*/
fun main() {
    var resposta: Int

    println("\n--- Lançador de Projéteis - Cenário 8 ---")

    do {
        println("Escolha uma opção:")
        println("1 - Simulação do gráfico")
        println("2 - Lista de combinações seguras")
        println("x - Sair")


        when (val opcao = readLine()) {
            "1" -> {
                simularGrafico()
                resposta = 1
            }

            "2" -> {
                resposta = 1
                listarCombinacoesSeguras()
            }

            "x" -> {
                println("Fim do programa")
                resposta = 0
            }

            else -> {
                println("Opção inválida!")
                resposta = 1
            }
        }
    } while (resposta != 0)
}

fun simularGrafico() {
    val resetColor = "\u001B[0m"
    val red = "\u001B[31;1m"
    val red2 = "\u001B[31m"
    val green = "\u001B[32m"
    val blue = "\u001B[34m"
    val mangenta = "\u001b[35m"
    val ciano = "\u001b[36m"


    val gravidadeTerra = 9.8 // gravidade da Terra em (m/s)^2

    println("Introduza o ângulo de lançamento (0-90°):")
    val anguloGraus = readLine()!!.toDoubleOrNull()

    //valida a entrada do ângulo inserido pelo user
    if (anguloGraus == null || anguloGraus < 0 || anguloGraus > 90) {
        println("Ângulo inválido")
        return
    }

    println("Introduza a altura inicial (m):")
    val alturaInicial = readLine()!!.toDoubleOrNull()

    //valida a alturna inicial inserida pelo user
    if (alturaInicial == null || alturaInicial < 0) {
        println("Altura inicial inválida")
        return
    }

    //se a angulo e altura for 0 dizemos que ambos são inválidos de inserir pelo utilizador
    if (anguloGraus == 0.0 && alturaInicial == 0.0) {
        println("Ângulo e altura inicial inválida.")
        return
    }

    println("Introduza a altura da rede de segurança (m):")
    val alturaRedeSeguranca = readLine()!!.toDoubleOrNull() ?: return

    //valida a altura da rede de segurança
    if (alturaRedeSeguranca < 0) {
        println("Altura da rede de segurança inválida")
        return
    }

    println("Introduza a que distância horizontal se encontra a rede de segurança (m):")
    val distanciaHorizontalRede = readLine()!!.toDoubleOrNull() ?: return

    //valida distância horizontal da rede inserida pelo utilizador
    if (distanciaHorizontalRede < 0) {
        println("Distancia horizontal da rede de segurança inválida.")
        return
    }

    val anguloRadianos = anguloGraus * PI / 180.0  //convertemos o angulo para radianos

    val seno = sin(anguloRadianos) //calcula o seno do angulo

    val cosseno = cos(anguloRadianos) //calcula o cosseno do angulo

    val alcanceH = (distanciaHorizontalRede / cosseno).pow(2) //calculo do alcance na horizontal

    val alturaFinal = alturaInicial + (distanciaHorizontalRede * seno / cosseno) //calculo da altura final

    val velocidadeInicial = ((gravidadeTerra * alcanceH) / alturaFinal).pow(0.5) //calculo da velocidade inicial

    val grafico = Chart(80, 20)

    println("${blue}Eixo X: Distância percorrida (m)${resetColor}")
    println("${mangenta}Eixo Y: Altura (m)${resetColor}")

    var x = 0.0
    var y: Double? = alturaInicial

    var atingiuAlturaRede = false // flag para verificar se atingiu a altura da rede antes de alcançar a posição da rede

    while (y != null && y >= 0.0) {
        val novoY =
            alturaInicial + (tan(anguloRadianos) * x) - ((gravidadeTerra * x * x) / (2 * velocidadeInicial * velocidadeInicial * cos(
                anguloRadianos
            ) * cos(anguloRadianos)))
        x += 1.0
        y = novoY
        grafico.ponto(x, y)

        // Verificar se a altura atinge ou ultrapassa a altura da rede antes de alcançar a posição da rede
        if (y <= alturaRedeSeguranca) {
            atingiuAlturaRede = true
        }

        // Verificar se a altura na posição da rede é menor ou igual à altura da rede e se já ultrapassou essa altura antes
        if (x >= distanciaHorizontalRede && atingiuAlturaRede && y <= alturaRedeSeguranca) {
            break
        }
    }
    grafico.draw()

    val alcanceHorizontal = (velocidadeInicial * velocidadeInicial * seno * cosseno) / gravidadeTerra

    val tempoSubida = (velocidadeInicial * seno) / gravidadeTerra

    val tempoVoo = 2 * tempoSubida

    val alcanceMaximo = alcanceHorizontal + distanciaHorizontalRede

    val alturaMaxima = alturaInicial + (velocidadeInicial * velocidadeInicial * seno * seno) / (2 * gravidadeTerra)

    // Verifica se a aterragem ocorre na altura da rede de segurança ou abaixo dela quando o alcance horizontal alcança a distância horizontal da rede
    if ((alturaMaxima ?: 0.0) <= alturaRedeSeguranca && x >= distanciaHorizontalRede) {
        println("\n${green}Aterragem segura na rede de segurança!${resetColor}\n")
        println("Valores ao atingir a rede de segurança:\n")
        println("${ciano}Tempo de subida:${resetColor} ${"%.2f".format(tempoSubida)} s")
        println("${ciano}Tempo de voo:${resetColor} ${"%.2f".format(tempoVoo)}s")
        println("${ciano}Velocidade Inicial:${resetColor} ${"%.2f".format(velocidadeInicial)}m/s")
        println("${ciano}Ângulo de lançamento:${resetColor} ${"%.2f".format(anguloGraus)}°")
        println("${ciano}Alcance Máximo:${resetColor} ${"%.2f".format(alcanceMaximo)}m")
        println("${ciano}Altura Máxima:${resetColor} ${"%.2f".format(alturaMaxima)}m\n")
    } else {
        println("\n${red}Aterragem fora da rede de segurança!${resetColor}")
        println("${red2}Não foi possível atingir a rede com os valores fornecidos.${resetColor}\n")
    }
}

fun listarCombinacoesSeguras() {
    val gravidadeTerra = 9.8 // gravidade da Terra em (m/s)^2

    println("Digite a altura inicial (m):")
    val alturaInicial = readLine()?.toDoubleOrNull() ?: return

    if (alturaInicial < 0) {
        println("Altura inicial inválida")
        return
    }

    println("Digite a altura da rede de segurança (m):")
    val alturaRedeSeguranca = readLine()?.toDoubleOrNull() ?: return

    if (alturaRedeSeguranca < 0) {
        println("Altura de rede de segurança inválida")
        return
    }

    println("Digite a distância horizontal para a rede de segurança (m):")
    val distanciaHorizontalRede = readLine()?.toDoubleOrNull() ?: return

    if (distanciaHorizontalRede < 0) {
        println("Distância horizontal de rede inválida")
        return
    }

    println("Lista de combinações seguras para atingir a rede de segurança:")
    println("|Ângulo | Altura|")

    for (angulo in 1..90) {
        val anguloRadianos = angulo * PI / 180.0

        for (altura in 1..alturaInicial.toInt()) {
            val seno = sin(anguloRadianos)
            val cosseno = cos(anguloRadianos)
            val alcanceHorizontal = (distanciaHorizontalRede / cosseno).pow(2) // cálculo do alcance na horizontal
            val alturaFinal = alturaInicial + (distanciaHorizontalRede * seno / cosseno)
            val velocidadeInicial = ((gravidadeTerra * alcanceHorizontal) / alturaFinal).pow(0.5)
            val alturaMaxima =
                alturaInicial + (velocidadeInicial * velocidadeInicial * seno * seno) / (2 * gravidadeTerra)

            if (alcanceHorizontal >= distanciaHorizontalRede && alturaMaxima <= alturaRedeSeguranca) {
                if (angulo > 9) {
                    if (altura > 9) {
                        println("| ${angulo}°   | ${altura}m   |")
                    } else {
                        println("| ${angulo}°   | ${altura}m    |")
                    }
                }
                if (angulo < 10) {
                    if (altura > 9) {
                        println("| ${angulo}°    | ${altura}m   |")
                    } else {
                        println("| ${angulo}°    | ${altura}m    |")
                    }
                }
            }
        }
    }


}






