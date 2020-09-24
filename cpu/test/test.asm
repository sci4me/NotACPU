    ld a zero
    ld b one
loop_a:
    add a
    jc loop_b
    jmp loop_a
loop_b:
    sub a
    jz loop_a
    jmp loop_b

zero: .db 0
one: .db 1