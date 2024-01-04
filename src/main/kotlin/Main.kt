fun main() {

    // vou definir um gráfico com 80 caracteres de largura por 25 caracteres de altura
    val larguraGrafico = 80
    val alturaGrafico = 25
    val grafico = Chart(larguraGrafico, alturaGrafico)

    println()
    println("************************************")
    println("************* Exemplo **************")
    println("************************************")
    println()


    // vou adicionar 5 pontos ao gráfico
    // os pontos são representados por dois Double: o x e o y
    grafico.ponto(1.0, 1.0)
    grafico.ponto(2.0, 3.0)
    grafico.ponto(3.0, 5.0)
    grafico.ponto(4.0, 4.0)
    grafico.ponto(5.0, 1.0)

    // mando desenhar o gráfico com os pontos previamente introduzidos
    grafico.draw()

}