package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String jsonFilePath = "C:\\Users\\umberto alves\\Desktop\\Faturamento\\faturamento.json";
        String jsonData = readJsonFile(jsonFilePath);

        if (jsonData != null) {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray faturamentoDiario = jsonObject.getJSONArray("faturamentoDiario");

            // Variáveis para armazenar o menor e maior faturamento e cálculo da média
            double menorFaturamento = Double.MAX_VALUE;
            double maiorFaturamento = Double.MIN_VALUE;
            double somaFaturamento = 0;
            int diasComFaturamento = 0;

            // Calcula o menor e maior faturamento, bem como a soma total e dias com faturamento
            for (int i = 0; i < faturamentoDiario.length(); i++) {
                double faturamento = faturamentoDiario.getDouble(i);
                if (faturamento > 0) { // Ignora os dias sem faturamento
                    if (faturamento < menorFaturamento) {
                        menorFaturamento = faturamento;
                    }
                    if (faturamento > maiorFaturamento) {
                        maiorFaturamento = faturamento;
                    }
                    somaFaturamento += faturamento;
                    diasComFaturamento++;
                }
            }

            // Calcula a média de faturamento considerando apenas os dias com faturamento
            double mediaFaturamento = somaFaturamento / diasComFaturamento;

            // Calcula o número de dias com faturamento acima da média
            int diasAcimaDaMedia = 0;
            for (int i = 0; i < faturamentoDiario.length(); i++) {
                double faturamento = faturamentoDiario.getDouble(i);
                if (faturamento > 0 && faturamento > mediaFaturamento) {
                    diasAcimaDaMedia++;
                }
            }

            // Exibe os resultados
            System.out.printf("Menor valor de faturamento: %.2f%n", menorFaturamento);
            System.out.printf("Maior valor de faturamento: %.2f%n", maiorFaturamento);
            System.out.printf("Número de dias com faturamento acima da média: %d%n", diasAcimaDaMedia);
        } else {
            System.out.println("Erro ao ler o arquivo JSON.");
        }
    }

    private static String readJsonFile(String filePath) {
        StringBuilder jsonData = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonData.toString();
    }
}
