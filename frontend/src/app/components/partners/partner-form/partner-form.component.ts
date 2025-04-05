import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { PartnerService } from '../../../services/partner.service';
import { Partner, Direction, FlowType } from '../../../models/partner.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-partner-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
    RouterModule
  ],
  templateUrl: './partner-form.component.html',
  styleUrls: ['./partner-form.component.scss']
})
export class PartnerFormComponent implements OnInit {
  directions = Object.values(Direction);
  flowTypes = Object.values(FlowType);
  isEdit = false;

  partnerForm = this.fb.group({
    alias: ['', Validators.required],
    type: ['', Validators.required],
    direction: [null as Direction | null, Validators.required],
    application: [''],
    processedFlowType: [null as FlowType | null],
    description: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private partnerService: PartnerService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.partnerService.getPartnerById(+id).subscribe(partner => {
        this.partnerForm.patchValue(partner);
      });
    }
  }

  onSubmit() {
    if (this.partnerForm.invalid) return;

    const partner = this.partnerForm.value as Partner;
    const operation = this.isEdit 
      ? this.partnerService.updatePartner(partner.id!, partner)
      : this.partnerService.createPartner(partner);

    operation.subscribe({
      next: () => this.router.navigate(['/partners']),
      error: err => console.error('Operation failed', err)
    });
  }
}