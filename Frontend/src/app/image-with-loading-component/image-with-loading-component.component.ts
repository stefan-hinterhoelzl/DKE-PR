import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-image-with-loading',
  templateUrl: './image-with-loading-component.component.html',
  styleUrls: ['./image-with-loading-component.component.css']
})
export class ImageWithLoadingComponentComponent {

  @Input() loader:string='https://media.tenor.com/images/f864cbf3ea7916572605edd3b3fe637f/tenor.gif';
  @Input() height:number=200;
  @Input() width:number=200;
  @Input() image:string;

  isLoading:boolean;
  
  constructor() { 
    this.isLoading=true;
  }

  hideLoader(){
    this.isLoading=false;
  }

}