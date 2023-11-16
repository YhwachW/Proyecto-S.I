import tkinter as tk
from tkinter import ttk, messagebox
import pandas as pd
from datetime import datetime

class SistemaPedidos:
    def __init__(self):
        self.pedidos_individuales = pd.DataFrame(columns=['Producto', 'Cantidad', 'Fecha'])
        self.pedidos_acumulados = pd.DataFrame(columns=['Producto', 'Cantidad', 'Fecha'])

    def agregar_pedido(self, producto, cantidad):
        fecha_actual = datetime.now()
        fecha_formato = fecha_actual.strftime("%Y-%m-%d %H:%M:%S")

        nuevo_pedido_individual = pd.DataFrame({'Producto': [producto], 'Cantidad': [cantidad], 'Fecha': [fecha_formato]})
        nuevo_pedido_acumulado = pd.DataFrame({'Producto': [producto], 'Cantidad': [cantidad], 'Fecha': [fecha_formato]})

        self.pedidos_individuales = pd.concat([self.pedidos_individuales, nuevo_pedido_individual], ignore_index=True)
        self.pedidos_acumulados = pd.concat([self.pedidos_acumulados, nuevo_pedido_acumulado], ignore_index=True)

    def guardar_en_excel(self, tipo_pedidos):
        nombre_archivo = f"pedidos_{datetime.now().strftime('%Y%m%d%H%M%S')}_{tipo_pedidos}.xlsx"
        if tipo_pedidos == 'individuales':
            self.pedidos_individuales.to_excel(nombre_archivo, index=False)
        elif tipo_pedidos == 'acumulados':
            self.pedidos_acumulados.to_excel(nombre_archivo, index=False)
        messagebox.showinfo("Guardado", f"Pedidos guardados en {nombre_archivo}")

def agregar_pedido():
    producto = producto_entry.get()
    cantidad = cantidad_entry.get()
    
    if producto and cantidad:
        sistema.agregar_pedido(producto, cantidad)
        actualizar_resultado_text()

def acumular_pedidos():
    sistema.pedidos_acumulados = pd.concat([sistema.pedidos_acumulados, sistema.pedidos_individuales], ignore_index=True)
    sistema.pedidos_individuales = pd.DataFrame(columns=['Producto', 'Cantidad', 'Fecha'])
    actualizar_resultado_text()

def guardar_pedidos_acumulados():
    sistema.guardar_en_excel('acumulados')

def actualizar_resultado_text():
    resultado_text.config(state=tk.NORMAL)
    resultado_text.delete("1.0", tk.END)
    resultado_text.insert(tk.END, sistema.pedidos_individuales.to_string(index=False))
    resultado_text.config(state=tk.DISABLED)

# Crear instancia del sistema
sistema = SistemaPedidos()

# Crear ventana
ventana = tk.Tk()
ventana.title("Sistema de Pedidos")

# Crear pestañas
pestañas = ttk.Notebook(ventana)

# Pestaña de pedidos individuales
pestaña_individual = ttk.Frame(pestañas)
pestañas.add(pestaña_individual, text='Individuales')

# Posicionar widgets en la pestaña de pedidos individuales usando solo pack
tk.Label(pestaña_individual, text="Producto:").pack(padx=10, pady=5, side=tk.LEFT)
producto_entry = tk.Entry(pestaña_individual)
producto_entry.pack(padx=10, pady=5, side=tk.LEFT)

tk.Label(pestaña_individual, text="Cantidad:").pack(padx=10, pady=5, side=tk.LEFT)
cantidad_entry = tk.Entry(pestaña_individual)
cantidad_entry.pack(padx=10, pady=5, side=tk.LEFT)

agregar_button = tk.Button(pestaña_individual, text="Agregar Pedido", command=agregar_pedido)
agregar_button.pack(padx=10, pady=5, side=tk.LEFT)

borrar_button = tk.Button(pestaña_individual, text="Borrar Datos", command=lambda: borrar_datos(pestaña_individual))
borrar_button.pack(padx=10, pady=5, side=tk.LEFT)

guardar_button = tk.Button(pestaña_individual, text="Guardar Pedidos", command=lambda: guardar_pedidos('individuales'))
guardar_button.pack(padx=10, pady=5, side=tk.LEFT)

acumular_button = tk.Button(pestaña_individual, text="Acumular Pedidos", command=acumular_pedidos)
acumular_button.pack(padx=10, pady=5, side=tk.LEFT)

resultado_text = tk.Text(pestaña_individual, height=10, width=40)
resultado_text.insert(tk.END, sistema.pedidos_individuales.to_string(index=False))
resultado_text.config(state=tk.DISABLED)
resultado_text.pack(padx=10, pady=5)

# Pestaña de pedidos acumulados
pestaña_acumulados = ttk.Frame(pestañas)
pestañas.add(pestaña_acumulados, text='Acumulados')

# Posicionar widgets en la pestaña de pedidos acumulados usando solo pack
tk.Label(pestaña_acumulados, text="Producto:").pack(padx=10, pady=5, side=tk.LEFT)

guardar_acumulados_button = tk.Button(pestaña_acumulados, text="Guardar Pedidos Acumulados", command=guardar_pedidos_acumulados)
guardar_acumulados_button.pack(padx=10, pady=5, side=tk.LEFT)

resultado_acumulados_text = tk.Text(pestaña_acumulados, height=10, width=40)
resultado_acumulados_text.insert(tk.END, sistema.pedidos_acumulados.to_string(index=False))
resultado_acumulados_text.config(state=tk.DISABLED)
resultado_acumulados_text.pack(padx=10, pady=5)

# Función para borrar datos
def borrar_datos(pestaña):
    if pestaña == pestaña_individual:
        sistema.pedidos_individuales = pd.DataFrame(columns=['Producto', 'Cantidad', 'Fecha'])
        actualizar_resultado_text()

# Función para guardar pedidos
def guardar_pedidos(tipo_pedidos):
    sistema.guardar_en_excel(tipo_pedidos)

# Iniciar bucle de la interfaz gráfica
ventana.mainloop()

# Al cerrar la ventana, guardar los pedidos en archivos de Excel
sistema.guardar_en_excel('individuales')
sistema.guardar_en_excel('acumulados')