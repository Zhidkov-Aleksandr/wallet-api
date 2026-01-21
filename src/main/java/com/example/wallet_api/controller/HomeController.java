package com.example.wallet_api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!DOCTYPE html>
                <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <title>Wallet API</title>
                    <style>
                        body {
                            font-family: monospace;
                            background-color: #f7f7f7;
                            padding: 20px;
                        }
                        pre {
                            background-color: #ffffff;
                            padding: 20px;
                            border-radius: 6px;
                            box-shadow: 0 0 10px rgba(0,0,0,0.05);
                            line-height: 1.5;
                        }
                        h1 {
                            margin-top: 0;
                        }
                    </style>
                </head>
                <body>
                
                <h1>Wallet API</h1>
                
                <pre>
Это простой REST-сервис для управления балансом кошельков.

Демо-кошелёк
------------
ID кошелька: 00000000-0000-0000-0000-000000000001
Начальный баланс: 1000

Доступные операции
------------------

1) Получить баланс кошелька
   GET /api/v1/wallets/{walletId}

   Пример:
   curl http://localhost:8080/api/v1/wallets/00000000-0000-0000-0000-000000000001


2) Пополнить баланс
   POST /api/v1/wallet

   Пример:
   curl -X POST http://localhost:8080/api/v1/wallet \\
        -H "Content-Type: application/json" \\
        -d '{
              "walletId": "00000000-0000-0000-0000-000000000001",
              "operationType": "DEPOSIT",
              "amount": 500
            }'


3) Списать средства
   POST /api/v1/wallet

   Пример:
   curl -X POST http://localhost:8080/api/v1/wallet \\
        -H "Content-Type: application/json" \\
        -d '{
              "walletId": "00000000-0000-0000-0000-000000000001",
              "operationType": "WITHDRAW",
              "amount": 200
            }'
                </pre>
                
                </body>
                </html>
                """;
    }
}
