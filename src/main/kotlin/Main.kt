import kotlin.math.*

/*
Enunciado 8:
Num recinto de jogos radicais um canhão lança um jogador que tem de atingir uma rede vertical de segurança que se
encontra à distância horizontal de 40m do canhão. A rede tem uma altura de 5m.
(a) - Determine valores de velocidade inicial e de ângulo de lançamento que permitem que o jogador atinja a rede de
segurança;
(b) - Trace a trajétoria do jogador durante o voo.
O programa deve permitir alterar as condições iniciais do lançamento para poder simular diferentes
cenários de voo.
Sugestão: Calcule também o tempo de voo e tempo de subida.
*/

fun main() {
    var resposta: Int

    println("--- Cenário 8: Num recinto de jogos radicais um canhão lança um jogador que tem de atingir uma rede " +
            "vertical que se encontra à distância horizontal de 40m do canhão. A rede tem uma altura de 5m ---")

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

    val gravidadeTerra = 9.8 //gravidade da Terra em (m/s)^2
    val gravidadePedida = 2 * gravidadeTerra

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
    val alturaRedeSeguranca = readLine()!!.toDoubleOrNull() //altura da rede de segurança em metros

    if(alturaRedeSeguranca == null || alturaRedeSeguranca < 0){
        println("Altura da rede de segurança inválida")
        return
    }

    println("Introduza a que distância horizontal se encontra a rede de segurança (m):")
    val distanciaHorizontalRede = readLine()!!.toDoubleOrNull() //distância horizontal da rede de segurança em metros

    if (distanciaHorizontalRede == null || distanciaHorizontalRede < 0){
        println("Distancia horizontal da rede de segurança inválida.")
        return
    }

    val anguloRadianos = anguloGraus * PI / 180
    val seno = sin(anguloRadianos)
    val cosseno = cos(anguloRadianos)
    val alcanceH = (distanciaHorizontalRede / cosseno).pow(2) // calculo do alcance na horizontal
    val alturaFinal = alturaInicial + (distanciaHorizontalRede * seno / cosseno)
    val velocidadeInicial = ((gravidadeTerra * alcanceH) / alturaFinal).pow(0.5)

    val grafico = Chart(100, 30)

    println("${redColor}Eixo X: Distância percorrida (m)${resetColor}")
    println("${greenColor}Eixo Y: Altura (m)${resetColor}")

    var x = 0.0
    var y: Double? = alturaInicial

    while (y != null && y >= 0.0) {
        grafico.ponto(x, y)
        x += 2.0
        // calculo da próxima altura usando a equação do movimento com aceleração constante
        val novoY = alturaInicial + (tan(anguloRadianos) * x) - ((gravidadePedida * x * x) / (2 * velocidadeInicial
                * velocidadeInicial * cos(anguloRadianos) * cos(anguloRadianos)))
        if (novoY < 0.0) break // encerra o loop se a altura (y) for menor que 0
        y = novoY
    }

    grafico.draw()

    val alcanceHorizontal = velocidadeInicial * velocidadeInicial * seno * 2 * cosseno / gravidadePedida
    val aterragemSegura = alturaInicial - (gravidadePedida * alcanceHorizontal.pow(2)) /
            (2 * velocidadeInicial.pow(2) * cosseno.pow(2))
    val tempoSubida = (velocidadeInicial * seno) / gravidadePedida
    val tempoVoo = 2 * tempoSubida

    if (aterragemSegura in 0.0..alturaRedeSeguranca) {
        println("\nAterragem segura na rede de segurança!")
        println("Valores ao atingir a rede de segurança:")
        println("Tempo de subida: $tempoSubida s")
        println("Tempo de voo: $tempoVoo s")
        println("Velocidade Inicial: $velocidadeInicial m/s")
        println("Ângulo de lançamento: $anguloGraus°")
    } else {
        println("\nAterragem fora da rede de segurança!")
        println("Não foi possível atingir a rede com os valores fornecidos.")
    }
}

fun listarCombinacoesSeguras() {
    val gravidadeTerra = 9.8 //gravidade da Terra em (m/s)^2
    val gravidadePedida = 2 * gravidadeTerra

    println("\nIntroduza a altura inicial máxima desejada:")
    val alturaInicialMaxima = readLine()!!.toDoubleOrNull()

    if(alturaInicialMaxima == null || alturaInicialMaxima <0){
        println("Altura inicial máxima inválida.")
        return
    }

    println("Introduza a altura da rede de segurança (m):")
    val alturaRedeSeguranca = readLine()!!.toDoubleOrNull() //altura da rede de segurança em metros

    if(alturaRedeSeguranca == null || alturaRedeSeguranca < 0){
        println("Altura da rede de segurança inválida")
        return
    }

    println("Introduza a que distância horizontal se encontra a rede de segurança (m):")
    val distanciaHorizontalRede = readLine()!!.toDoubleOrNull() //distância horizontal da rede de segurança em metros

    if (distanciaHorizontalRede == null || distanciaHorizontalRede < 0){
        println("Distancia horizontal da rede de segurança inválida.")
        return
    }

    println("\nLista de combinações de valores disponíveis para aterrar com segurança:")
    println("| Ângulo | Altura |")
    println("|--------|--------|")

    // for para calcular e listar as combinaçòes seguras de ângulo e altura
    for (angulo in 1..90) {
        for (altura in 1..alturaInicialMaxima.toInt()) {
            val radianos = angulo * PI / 180
            val sinAngulo = sin(radianos)
            val cosAngulo = cos(radianos)

            //h relativa ao solo - eq.traj
            val parte1Altura = altura + (distanciaHorizontalRede * sinAngulo / cosAngulo)
            // calculo da velocidade para essa altura e angulo usando a equação de alcance horizontal
            val velocidade =
                ((gravidadeTerra * (distanciaHorizontalRede / cosAngulo).pow(2)) / parte1Altura).pow(0.5)
            // calculo do alcance horizontal para essa velocidade e ângulo
            val alcance = velocidade * velocidade * sinAngulo * 2 * cosAngulo / gravidadePedida
            // calculo da altura de aterragem utilizado a fórmula da trajétoria
            val aterragem =
                altura - (gravidadePedida * alcance * alcance) / (2 * velocidade * velocidade * cosAngulo * cosAngulo)

            // se a ateragem estiver entre 0 e a altura da rede imprime essa combinação como combinação segura
            if (aterragem >= 0 && aterragem <= alturaRedeSeguranca) {
                println("| ${angulo}°    | ${altura}m     |")
            }
        }
    }
}