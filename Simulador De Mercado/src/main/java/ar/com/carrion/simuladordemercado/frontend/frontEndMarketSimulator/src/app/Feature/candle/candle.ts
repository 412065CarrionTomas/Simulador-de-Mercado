import { Component, OnInit, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { createChart, IChartApi, CandlestickData } from 'lightweight-charts';

@Component({
  selector: 'app-candle-chart',
  template: '<div #chartContainer style="width: 100%; height: 500px;"></div>',
  standalone: true
})
export class CandlestickChartComponent implements OnInit, AfterViewInit {
  @ViewChild('chartContainer', { static: false }) chartContainer!: ElementRef;
  private chart: IChartApi | null = null;

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.createCandlestickChart();
  }

  createCandlestickChart(): void {
    if (!this.chartContainer) return;

    const container = this.chartContainer.nativeElement;

    this.chart = createChart(container, {
      width: container.clientWidth,
      height: 500,
      layout: {
        background: { color: '#222' } as any,
        textColor: '#DDD',
      },
      grid: {
        vertLines: { color: '#444' },
        horzLines: { color: '#444' },
      },
    });

    const candlestickSeries = this.chart.addCandlestickSeries({
      upColor: '#26a69a',
      downColor: '#ef5350',
      borderVisible: false,
      wickUpColor: '#26a69a',
      wickDownColor: '#ef5350',
    });

    const data: CandlestickData[] = [
      { time: '2024-01-01', open: 100, high: 110, low: 95, close: 105 },
      { time: '2024-01-02', open: 105, high: 115, low: 100, close: 108 },
      { time: '2024-01-03', open: 108, high: 120, low: 105, close: 112 },
      { time: '2024-01-04', open: 112, high: 118, low: 108, close: 110 },
      { time: '2024-01-05', open: 110, high: 114, low: 102, close: 106 },
      { time: '2024-01-06', open: 106, high: 112, low: 98, close: 100 },
      { time: '2024-01-07', open: 100, high: 108, low: 96, close: 104 },
      { time: '2024-01-08', open: 104, high: 116, low: 102, close: 114 },
      { time: '2024-01-09', open: 114, high: 125, low: 112, close: 122 },
      { time: '2024-01-10', open: 122, high: 128, low: 118, close: 120 },
    ];

    candlestickSeries.setData(data);
  }
}
