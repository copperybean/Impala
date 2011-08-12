// Copyright (c) 2011 Cloudera, Inc. All rights reserved.

#include "exprs/expr.h"  // contains SlotRef definition

#include "gen-cpp/Exprs_types.h"
#include "runtime/runtime-state.h"

using namespace std;

namespace impala {

SlotRef::SlotRef(const TExprNode& node)
  : Expr(node, true),
    null_indicator_offset_(0, 0),
    slot_id_(node.slot_ref.slot_id) {
    // slot_/null_indicator_offset_ are set in Prepare()
}

void SlotRef::Prepare(RuntimeState* state) {
  Expr::Prepare(state);
  const SlotDescriptor* slot_desc  = state->descs().GetSlotDescriptor(slot_id_);
  // TODO: report error
  if (slot_desc == NULL) return;
  // TODO(marcel): get from runtime state
  this->tuple_idx_ = 0;
  this->slot_offset_ = slot_desc->tuple_offset();
  this->null_indicator_offset_ = slot_desc->null_indicator_offset();
}

string SlotRef::DebugString() const {
  stringstream out;
  out << "SlotRef(slot_id=" << slot_id_
      << " tuple_idx=" << tuple_idx_ << " slot_offset=" << slot_offset_
      << " null_indicator=" << null_indicator_offset_
      << " " << Expr::DebugString() << ")";
  return out.str();
}

}
