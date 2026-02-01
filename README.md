📈 Simulador de Mercado (Market Simulator)

Este proyecto es un simulador de mercado financiero diseñado para modelar y experimentar con la microestructura de un mercado de trading. Está orientado a simular cómo se reciben, almacenan y ejecutan órdenes de compra y venta, y cómo a partir de estas interacciones surgen los precios y las velas de mercado.

El simulador está pensado como una herramienta didáctica y técnica, que permite:

Representar un Order Book real con órdenes de compra (bids) y venta (asks).

Simular la inserción de órdenes limit y órdenes de mercado (market orders).

Ejecutar el proceso de matching entre órdenes para generar trades.

Construir velas japonesas (OHLC) basadas en los resultados de las ejecuciones.

Probar distintas estrategias algorítmicas de mercado (por ejemplo, un algoritmo Random que actúa como maker o taker).

Este proyecto no es solo una colección de scripts experimentales: fue desarrollado con enfoque en la arquitectura y el diseño de dominio, permitiendo ampliar y adaptar el simulador con nuevas estrategias, servicios y componentes.
