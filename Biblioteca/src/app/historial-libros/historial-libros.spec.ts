import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialLibros } from './historial-libros';

describe('HistorialLibros', () => {
  let component: HistorialLibros;
  let fixture: ComponentFixture<HistorialLibros>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistorialLibros]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistorialLibros);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
