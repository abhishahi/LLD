import random

print(random.randint(1, 10)) # Returns a random integer between 1 and 10


r = range(0, -10,-2)
print(list(r) ) # Returns True nearly instant

row_data = bytearray(b'Hello')
row_data[0] = 73 # This will raise a TypeError because bytes are immutable
print(row_data.decode('utf-8')) # Returns Hello
print(type(row_data)) # Returns <class 'bytes'>
print(row_data) # Returns <class 'str'>
x = 1
y = 2.8




