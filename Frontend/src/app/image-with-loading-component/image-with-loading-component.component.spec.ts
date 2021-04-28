import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageWithLoadingComponentComponent } from './image-with-loading-component.component';

describe('ImageWithLoadingComponentComponent', () => {
  let component: ImageWithLoadingComponentComponent;
  let fixture: ComponentFixture<ImageWithLoadingComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImageWithLoadingComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageWithLoadingComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
