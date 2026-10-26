import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Bibliotecario } from './bibliotecario';

describe('Bibliotecario', () => {
  let component: Bibliotecario;
  let fixture: ComponentFixture<Bibliotecario>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Bibliotecario]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Bibliotecario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
