# Информационная система для агентства недвижимости

**Оглавление**

- [Задача](#Задача)
- [Основные понятия](#Основные-понятия)
- [Основные бизнес процессы компании](#Основные-бизнес-процессы-компании)
- [Общие требования](#Общие-требования)

## Задача
Разработать информационную систему для агенства недвижимости, которое помогает своим клиентам купить/продать объекты недвижимости (без аренды).

## Общие требования
* Не позволяйте пользователю вводить некорректные значения в текстовые поля сущностей. Например, в случае несоответствия типа данных поля введенному значению. Оповестите пользователя о совершенной им ошибке.
* Уведомляйте пользователя о совершаемых им ошибках и/или совершении запрещенного в рамках задания действиях.
* Запрещено удаление сущностей, которое приведет к нарушению ограничей связей. Например, запрещено удалять клиента, который связан с предложением или потребностью.
* При удалении любых сущностей необходимо либо спрашивать подтверждение пользователя, либо реализовать возможность отмены операции удаления.
* При возникновении непредвиденной ошибки приложение не должно аварийно завершать работу.

## Основные понятия
* <strong> Пользователь </strong> - пользователь системы. В рамках данного задания предстоит реализовать систему, упрощающую работу риэлтора.
* <strong> Риэтор </strong> - сотрудник компании агентства недвижимости. Выступает в качестве посредника между клиентом и компанией.
* <strong> Клиент </strong> - человек, который желает купить либо продать объект недвижимости.
* <strong> Объект недвижимости </strong> - недвижимое имущество, предмет сделки между клиентами. Обобщающее понятие для разных типов недвижимого имущества.
* <strong> Тип объекта недвижимости </strong> - квартира, дом, либо земельный участок.
* <strong> Потребность </strong> - желание клиента купить объект недвижимости, соответствующего указанным параметрам.
* <strong> Предложение </strong> - желание клиента продать указанный объект недвижимости за указанную цену.
* <strong> Сделка </strong> - факт осуществления продажи недвижимого имущества. В сделке участвуют две стороны: клиент-покупатель с потребностью и клиент-продавец с предложением.
* <strong> Стоимость услуг или комиссия </strong> - количество денег, которое должен заплатить клиент за оказанные ему компанией услуги.

## Основные бизнес процессы компании
Описание основного бизнес-процесса агентства недвижимости и участие в нем разрабатываемой системы: <br>
<strong> Продажа объекта недвижимости </strong> 
1. Клиент обращается в компанию с желанием продать объект недвижимости. В системе создаются три сущности: "Клиент", "Объект недвижимости", "Предложение"
2. "Предложению" клиента назначается ответственный риэлтор. Он осуществляет поиск подходящих потребностей
3. Клиент выбирает потребность и заключается сделка
4. Система рассчитывает размер комиссии, происходит оплата комиссии и перечисление средств риэлтору клиента-продавца и клиента-покупателя

<strong> Покупка объекта недвижимости </strong>
1. Клиент обращается в компанию с желанием купить объект недвижимости. В системе создаются дву сущности: "Клиент", "Потребность"
2. "Потребности" клиента назначается ответственный риэлтор. Он осуществляет поиск подходящих предложений
3. Клиент выбирает предложение и заключается сделка
4. Система рассчитывает размер комиссии, происходит оплата комиссии и перечисление средств риэлтору клиента-продавца и клиента-покупателя
