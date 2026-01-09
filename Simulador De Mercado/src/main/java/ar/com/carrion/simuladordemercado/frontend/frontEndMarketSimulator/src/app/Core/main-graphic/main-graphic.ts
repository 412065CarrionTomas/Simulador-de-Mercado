import { Component } from '@angular/core';
import { CandlestickChartComponent} from '../../Feature/candle/candle';

@Component({
  selector: 'app-main-graphic',
  standalone: true,
  imports: [CandlestickChartComponent],
  templateUrl: './main-graphic.html',
  styleUrl: './main-graphic.scss'
})
export class MainGraphicComponent {}
