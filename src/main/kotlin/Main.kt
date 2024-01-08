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

    println(
        "--- Cenário 8: Num recinto de jogos radicais um canhão lança um jogador que tem de atingir uma rede " +
                "vertical que se encontra à distância horizontal de 40m do canhão. A rede tem uma altura de 5m ---"
    )

    do {
        println("")
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
                listarCombinacoesSeguras()
                resposta = 1
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
    val redColor = "\u001B[31m"
    val greenColor = "\u001B[32m"

    val gravidadeTerra = 9.8 // gravidade da Terra em (m/s)^2

    println("Introduza o ângulo de lançamento (0-90°):")
    val anguloGraus = readLine()!!.toDoubleOrNull()

    if (anguloGraus == null || anguloGraus < 0 || anguloGraus > 90) {
        println("Ângulo inválido")
        return
    }

    println("Introduza a altura inicial (m):")
    val alturaInicial = readLine()!!.toDoubleOrNull()

    if (alturaInicial == null || alturaInicial < 0) {
        println("Altura inicial inválida")
        return
    }

    if (anguloGraus == 0.0 && alturaInicial == 0.0) {
        println("Ângulo e altura inicial inválida.")
        return
    }

    println("Introduza a altura da rede de segurança (m):")
    val alturaRedeSeguranca = readLine()!!.toDoubleOrNull() ?: return

    if (alturaRedeSeguranca < 0) {
        println("Altura da rede de segurança inválida")
        return
    }

    println("Introduza a que distância horizontal se encontra a rede de segurança (m):")
    val distanciaHorizontalRede = readLine()!!.toDoubleOrNull() ?: return

    if (distanciaHorizontalRede < 0) {
        println("Distancia horizontal da rede de segurança inválida.")
        return
    }

    val anguloRadianos = anguloGraus * PI / 180.0
    val seno = sin(anguloRadianos)
    val cosseno = cos(anguloRadianos)
    val alcanceH = (distanciaHorizontalRede / cosseno).pow(2) // cálculo do alcance na horizontal
    val alturaFinal = alturaInicial + (distanciaHorizontalRede * seno / cosseno)
    val velocidadeInicial = ((gravidadeTerra * alcanceH) / alturaFinal).pow(0.5)

    val grafico = Chart(80, 20)

    println("${redColor}Eixo X: Distância percorrida (m)${resetColor}")
    println("${greenColor}Eixo Y: Altura (m)${resetColor}")

    var x = 0.0
    var y: Double? = alturaInicial

    var atingiuAlturaRede = false // flag para verificar se atingiu a altura da rede antes de alcançar a posição da rede

    while (y != null && y >= 0.0) {
        val novoY = alturaInicial + (tan(anguloRadianos) * x) - ((gravidadeTerra * x * x) / (2 * velocidadeInicial * velocidadeInicial * cos(anguloRadianos) * cos(anguloRadianos)))
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
    val aterragemSegura = alturaInicial - (gravidadeTerra * alcanceHorizontal * alcanceHorizontal) / (2 * velocidadeInicial * velocidadeInicial * cosseno * cosseno)
    val tempoSubida = (velocidadeInicial * seno) / gravidadeTerra
    val tempoVoo = 2 * tempoSubida

    val alcanceMaximo = alcanceHorizontal + distanciaHorizontalRede
    val alturaMaxima = alturaInicial + (velocidadeInicial * velocidadeInicial * seno * seno) / (2 * gravidadeTerra)

    // Verifica se a aterragem ocorre na altura da rede de segurança ou abaixo dela quando o alcance horizontal alcança a distância horizontal da rede
    if ((alturaMaxima ?: 0.0) <= alturaRedeSeguranca && x >= distanciaHorizontalRede) {
        println("\nAterragem segura na rede de segurança!")
        println("Valores ao atingir a rede de segurança:")
        println("Tempo de subida: $tempoSubida s")
        println("Tempo de voo: $tempoVoo s")
        println("Velocidade Inicial: $velocidadeInicial m/s")
        println("Ângulo de lançamento: $anguloGraus°")
        println("Alcance Máximo: $alcanceMaximo m")
        println("Altura Máxima: $alturaMaxima m")
    } else {
        println("\nAterragem fora da rede de segurança!")
        println("Não foi possível atingir a rede com os valores fornecidos.")
    }
}
fun listarCombinacoesSeguras() {
    val gravidadeTerra = 9.8 // gravidade da Terra em (m/s)^2

    println("\nIntroduza a altura inicial máxima desejada:")
    val alturaInicialMaxima = readLine()!!.toDoubleOrNull()

    if (alturaInicialMaxima == null || alturaInicialMaxima < 0) {
        println("Altura inicial máxima inválida.")
        return
    }

    println("Introduza a altura da rede de segurança (m):")
    val alturaRedeSeguranca = readLine()!!.toDoubleOrNull() ?: return

    if (alturaRedeSeguranca < 0) {
        println("Altura da rede de segurança inválida")
        return
    }

    println("Introduza a que distância horizontal se encontra a rede de segurança (m):")
    val distanciaHorizontalRede = readLine()!!.toDoubleOrNull() ?: return

    if (distanciaHorizontalRede < 0) {
        println("Distancia horizontal da rede de segurança inválida.")
        return
    }

    var combinacoesSegurasEncontradas = false

    println("\nLista de combinações de valores disponíveis para aterrar com segurança:")
    println("| Ângulo | Altura |")
    println("|--------|--------|")

    for (angulo in 1..90) {
        for (altura in 1..alturaInicialMaxima.toInt()) {
            val radianos = angulo * PI / 180.0
            val sinAngulo = sin(radianos)
            val cosAngulo = cos(radianos)

            val parte1Altura = altura + distanciaHorizontalRede * sinAngulo / cosAngulo
            val velocidade = sqrt(gravidadeTerra * distanciaHorizontalRede * distanciaHorizontalRede / parte1Altura)
            val alcance = velocidade * velocidade * sinAngulo * 2.0 * cosAngulo / gravidadeTerra
            val aterragem = altura - gravidadeTerra * alcance * alcance / (2.0 * velocidade * velocidade * cosAngulo * cosAngulo)

            // Verificar se a aterragem ocorre na altura da rede de segurança ou abaixo dela quando o alcance horizontal alcança a distância horizontal da rede
            if (aterragem <= alturaRedeSeguranca && alcance >= distanciaHorizontalRede) {

                if(altura>9){
                    println("| ${angulo}°    | ${altura}m    |")
                    combinacoesSegurasEncontradas = true
                } else if (altura<10){
                    println("| ${angulo}°    | ${altura}m     |")
                    combinacoesSegurasEncontradas = true

                }else if (angulo<10){
                    println("|  ${ angulo}°   | ${altura}m     |")
                    combinacoesSegurasEncontradas = true

                }     else{
                    println("| ${angulo}°    | ${altura}m     |")
                    combinacoesSegurasEncontradas = true
                }
            }
        }

        if (!combinacoesSegurasEncontradas) {
            println("Não foram encontradas combinações seguras para as condições dadas.")
        }
    }
}




