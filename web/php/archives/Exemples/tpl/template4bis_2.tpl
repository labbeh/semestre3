{% extends "template4.tpl" %}

{% block title %}Page Web{% endblock %}
{% block head %}
    {{ parent() }}
    <style type="text/css">
        .important { color: #336699; }
    </style>
{% endblock %}
{% block content %}
    <h1>Une page g&eacute;n&eacute;r&eacute;e par Twig </h1>
    <p class="important">
        Bienvenue dans cette page Twig
    </p>
{% endblock %}
