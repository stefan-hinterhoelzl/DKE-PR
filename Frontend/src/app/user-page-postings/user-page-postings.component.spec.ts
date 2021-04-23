import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPagePostingsComponent } from './user-page-postings.component';

describe('UserPagePostingsComponent', () => {
  let component: UserPagePostingsComponent;
  let fixture: ComponentFixture<UserPagePostingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPagePostingsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPagePostingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
